package me.bionicbeanie.mods;

import static net.minecraft.server.command.CommandManager.literal;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.dimension.DimensionType;

public class SaveCoordinates implements ModInitializer {
	
	@Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(
            		literal("sc")
            			.then(CommandManager.argument("desc", StringArgumentType.string())
            					.executes(context -> printCoordinates(context, getDescription(context)))));
        });
    }
	
	private static String getDimensionTypeString(ServerCommandSource source) {
		
		DimensionType dimensionType = source.getEntity().world.getDimension();
		
		if(dimensionType.isNatural()){
			return "Overworld";
		}
		
		if(dimensionType.isUltrawarm()){
			return "Nether";
		}
		
		return "End";
	}
	
	private static String getDescription(CommandContext<ServerCommandSource> context) {
		return StringArgumentType.getString(context, "desc");
	}
	
	private static int printCoordinates(CommandContext<ServerCommandSource> context, String description) {
		ServerCommandSource source 				= context.getSource();
    	String 				dimensionTypeString = getDimensionTypeString(source);
    	Vec3d				pos 				= source.getEntity().getPos();
    	long 				x 					= Math.round(pos.x);
    	long 				y 					= Math.round(pos.y);
    	long 				z 					= Math.round(pos.z);
    	
    	source.sendFeedback(new LiteralText(description + " at " + dimensionTypeString + " [ " + x + " , " + y + " , " + z + " ]"), false);
        return 1;
	}
}
