package me.constantindev.lwat;

import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.Session;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.lwjgl.opengl.GL11;

import java.net.Proxy;

public class LoginWithEmailScreen extends Screen {
    TextFieldWidget name;
    TextFieldWidget uuid;
    ButtonWidget save;
    boolean printError = false;
    int offsetY = 50;

    public LoginWithEmailScreen() {
        super(Text.of("LoginWithEmail"));
    }

    @Override
    public void tick() {
        name.tick();
        //test.setText("E");
        super.tick();
    }

    @Override
    protected void init() {
        int h = this.height / 2;
        int w = this.width / 2;
        //fill(matrices,0,0,this.width,this.height,0x33000000);
        Session s = MinecraftClient.getInstance().getSession();
        this.name = new TextFieldWidget(this.textRenderer, w - 200, h + (-offsetY), 400, 20, Text.of("none"));
        this.uuid = new TextFieldWidget(this.textRenderer, w - 200, h, 400, 20, Text.of("none"));
        this.save = new ButtonWidget(w - 75, h + offsetY + 25, 150, 20, Text.of("Login"), (btn) -> {
            YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) new YggdrasilAuthenticationService(Proxy.NO_PROXY,"").createUserAuthentication(Agent.MINECRAFT);
            auth.setUsername(this.name.getText());
            auth.setPassword(this.uuid.getText());
            try {
                auth.logIn();
                Config.newsession = new Session(auth.getSelectedProfile().getName(),auth.getSelectedProfile().getId().toString(),auth.getAuthenticatedToken(),"mojang");
                printError = false;
                assert this.client != null;
                this.client.openScreen(null);
            } catch (Exception exc) {
                printError = true;
            }

        });
        this.name.setEditable(true);
        this.name.setMaxLength(1000);
        this.uuid.setEditable(true);
        this.uuid.setMaxLength(1000);
        super.init();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        int w = this.width / 2;
        matrices.push();
        this.renderBackground(matrices);
        RenderSystem.enableBlend();
        GL11.glScaled(4, 4, 4);
        this.textRenderer.draw(matrices, "Email login", (w - this.textRenderer.getWidth("Email login") * 2) / 4, 50 / 4, 0xFFFFFFFF);

        GL11.glScaled(.25, .25, .25);
        if (printError) MinecraftClient.getInstance().textRenderer.draw(matrices,"Failed to login!",w-this.textRenderer.getWidth("Failed to login!")/2,100,0xFFFF5555);
        MinecraftClient.getInstance().textRenderer.draw(matrices, "Email", w - this.textRenderer.getWidth("Email") / 2, this.name.y - 20, 0xFFFFFFFF);
        this.name.render(matrices, mouseX, mouseY, delta);
        MinecraftClient.getInstance().textRenderer.draw(matrices, "Password", w - this.textRenderer.getWidth("Password") / 2, this.uuid.y - 20, 0xFFFFFFFF);
        this.uuid.render(matrices, mouseX, mouseY, delta);
        this.save.render(matrices, mouseX, mouseY, delta);
        //fill(matrices,0,0,this.width,this.height,0xAAFFFFFF);

        RenderSystem.disableBlend();
        matrices.pop();
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void resize(MinecraftClient client, int w, int height) {
        this.name.setWidth(w);
        this.uuid.setWidth(w);
        super.resize(client, w, height);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.save.mouseClicked(mouseX, mouseY, button);
        this.name.mouseClicked(mouseX, mouseY, button);
        this.uuid.mouseClicked(mouseX, mouseY, button);
        return false;
    }

    @Override
    public boolean charTyped(char chr, int keyCode) {
        this.name.charTyped(chr, keyCode);
        this.uuid.charTyped(chr, keyCode);
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        this.name.keyPressed(keyCode, scanCode, modifiers);
        this.uuid.keyPressed(keyCode, scanCode, modifiers);
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        this.name.keyReleased(keyCode, scanCode, modifiers);
        this.uuid.keyReleased(keyCode, scanCode, modifiers);
        return false;
    }
}
