package me.constantindev.lwat.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import me.constantindev.lwat.ModifyGui;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.Session;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen.class)
public class MainMenuMixin extends Screen {
    ButtonWidget bwd;

    protected MainMenuMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("RETURN"), method = "init")
    public void render(CallbackInfo ci) {
        //matrices.push();
        RenderSystem.enableBlend();
        ButtonWidget bw = new ButtonWidget(1, height-21, 150, 20, Text.of("Modify session"), (buttonWidget) -> {
            MinecraftClient.getInstance().openScreen(new ModifyGui(this));
        });
        this.addButton(bw);

        bwd = bw;
        RenderSystem.disableBlend();
        //matrices.pop();
    }

}
