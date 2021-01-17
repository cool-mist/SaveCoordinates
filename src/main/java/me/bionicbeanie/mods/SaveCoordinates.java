package me.bionicbeanie.mods;

import static net.minecraft.server.command.CommandManager.literal;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.network.MessageType;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.dimension.DimensionType;

public class SaveCoordinates implements ModInitializer {

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(literal("sc").then(CommandManager.argument("desc", StringArgumentType.string())
                    .executes(SaveCoordinates::printCoordinates)));
        });
    }

    private static String getDimensionTypeString(ServerCommandSource source) {
        // Does not work with modded dimensions

        DimensionType dimensionType = source.getEntity().world.getDimension();

        if (dimensionType.isNatural()) {
            return "Overworld";
        }

        if (dimensionType.isUltrawarm()) {
            return "Nether";
        }

        return "End";
    }

    private static String getDescription(CommandContext<ServerCommandSource> context) {
        return StringArgumentType.getString(context, "desc");
    }

    private static int printCoordinates(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerCommandSource source = context.getSource();
        String dimensionTypeString = getDimensionTypeString(source);
        String description = getDescription(context);
        Vec3d pos = source.getEntity().getPos();
        long x = Math.round(pos.x);
        long y = Math.round(pos.y);
        long z = Math.round(pos.z);
        Text text = new LiteralText( "<Coordinates> " +
                description + " at " + dimensionTypeString + " [ " + x + " , " + y + " , " + z + " ]");
        PlayerManager playerManager = source.getMinecraftServer().getPlayerManager();

        playerManager.broadcastChatMessage(text, MessageType.CHAT,source.getPlayer().getUuid());
        return 1;
    }
}
