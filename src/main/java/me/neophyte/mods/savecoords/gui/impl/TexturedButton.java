package me.neophyte.mods.savecoords.gui.impl;

import io.github.cottonmc.cotton.gui.client.ScreenDrawing;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class TexturedButton extends WButton {

    private Identifier texture;

    public TexturedButton(Text label) {
        super(label);
    }

    public void setTexture(Identifier texture) {
        this.texture = texture;
    }

    @Override
    public void paint(MatrixStack matrices, int x, int y, int mouseX, int mouseY) {
        super.paint(matrices, x, y, mouseX, mouseY);

        if (texture != null) {
            ScreenDrawing.texturedGuiRect(matrices, x + 2, y + 2, 14, 16, texture, 0, 0, 0xFFFFFF);
        }

        if (getLabel() != null) {
            int color = 0xE0E0E0;
            if (!isEnabled()) {
                color = 0xA0A0A0;
            }

            int xOffset = (getIcon() != null && alignment == HorizontalAlignment.LEFT) ? 18 : 0;
            ScreenDrawing.drawStringWithShadow(matrices, getLabel().asOrderedText(), alignment, x + xOffset,
                    y + ((20 - 8) / 2), width, color);
        }
    }
}
