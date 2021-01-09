package me.constantindev.lwat.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import me.constantindev.lwat.ModifyGui;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.Session;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(net.minecraft.client.gui.screen.TitleScreen.class)
public class MainMenuMixin {
    ButtonWidget bwd;

    @Inject(at = @At("RETURN"), method = "render")
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        //matrices.push();

        Session s = MinecraftClient.getInstance().getSession();
        RenderSystem.enableBlend();
        MinecraftClient.getInstance().textRenderer.draw(matrices, Text.of("Name: " + s.getUsername()), 5, 5, 0xFFFFFFFF);
        MinecraftClient.getInstance().textRenderer.draw(matrices, Text.of("UUID: " + s.getUuid()), 5, 15, 0xFFFFFFFF);
        MinecraftClient.getInstance().textRenderer.draw(matrices, Text.of("Session ID: " + s.getSessionId()), 5, 25, 0xFFFFFFFF);
        MinecraftClient.getInstance().textRenderer.draw(matrices, Text.of("Access token: Lmao you thought"), 5, 35, 0xFFFFFFFF);
        ButtonWidget bw = new ButtonWidget(5, 45, 150, 20, Text.of("Modify"), (buttonWidget) -> {
            MinecraftClient.getInstance().openScreen(new ModifyGui());
        });
        bw.render(matrices, mouseX, mouseY, delta);
        bwd = bw;
        RenderSystem.disableBlend();
        //matrices.pop();
    }

    @Inject(at = @At("HEAD"), method = "mouseClicked")
    public void mouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        this.bwd.mouseClicked(mouseX, mouseY, button);

    }
}
