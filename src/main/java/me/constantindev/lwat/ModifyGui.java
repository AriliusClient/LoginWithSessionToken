package me.constantindev.lwat;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.Session;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class ModifyGui extends Screen {
    TextFieldWidget name;
    TextFieldWidget uuid;
    TextFieldWidget id;
    TextFieldWidget session;
    ButtonWidget save;
    ButtonWidget exit;
    int offsetY = 50;
    public ModifyGui() {
        super(Text.of("ModifySession"));
    }

    @Override
    public void tick() {
        name.tick();
        //test.setText("E");
        super.tick();
    }

    @Override
    protected void init() {
        int h = this.height/2;
        int w = this.width/2;
        //fill(matrices,0,0,this.width,this.height,0x33000000);
        Session s = MinecraftClient.getInstance().getSession();
        this.name    = new TextFieldWidget(this.textRenderer,w-200,h+offsetY*-2,400,20,Text.of("none"));
        this.uuid    = new TextFieldWidget(this.textRenderer,w-200,h+offsetY*-1,400,20,Text.of("none"));
        this.id      = new TextFieldWidget(this.textRenderer,w-200,h+offsetY*0,400,20,Text.of("none"));
        this.session = new TextFieldWidget(this.textRenderer,w-200,h+offsetY*1,400,20,Text.of("none"));
        this.save = new ButtonWidget(w-200,h+offsetY+25,150,20,Text.of("Save config"),(btn)->{
            Config.newsession = new Session(this.name.getText(),this.uuid.getText(),this.session.getText(),"legacy");
            assert this.client != null;
            this.client.openScreen(null);
        });
        this.exit = new ButtonWidget(w+200-150,h+offsetY+25,150,20,Text.of("Cancel"),(btn)->{
            assert this.client != null;
            this.client.openScreen(null);
        });
        this.name.setEditable(true);
        this.name.setMaxLength(1000);
        this.name.setText(s.getUsername());
        this.uuid.setEditable(true);
        this.uuid.setMaxLength(1000);
        this.uuid.setText(s.getUuid());
        this.id.setEditable(false);
        this.id.setMaxLength(1000);
        this.id.setText(s.getSessionId());
        this.session.setEditable(true);
        this.session.setMaxLength(1000);
        this.session.setText(s.getAccessToken());
        super.init();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        int w = this.width/2;
        matrices.push();
        this.renderBackground(matrices);
        RenderSystem.enableBlend();
        GL11.glScaled(4,4,4);
        this.textRenderer.draw(matrices,"Session stealer",(w-this.textRenderer.getWidth("Session stealer")*2)/4,50/4,0xFFFFFFFF);

        GL11.glScaled(.25,.25,.25);
        MinecraftClient.getInstance().textRenderer.draw(matrices,"Username",w-this.textRenderer.getWidth("Username")/2,this.name.y-20,0xFFFFFFFF);
        this.name.render(matrices, mouseX, mouseY, delta);
        MinecraftClient.getInstance().textRenderer.draw(matrices,"UUID",w-this.textRenderer.getWidth("UUID")/2,this.uuid.y-20,0xFFFFFFFF);
        this.uuid.render(matrices, mouseX, mouseY, delta);
        MinecraftClient.getInstance().textRenderer.draw(matrices,"Session ID (dont modify)",w-this.textRenderer.getWidth("Session ID (dont modify)")/2,this.id.y-20,0xFFFFFFFF);
        this.id.render(matrices, mouseX, mouseY, delta);
        MinecraftClient.getInstance().textRenderer.draw(matrices,"Token",w-this.textRenderer.getWidth("Token")/2,this.session.y-20,0xFFFFFFFF);
        this.session.render(matrices, mouseX, mouseY, delta);
        this.save.render(matrices, mouseX, mouseY, delta);
        this.exit.render(matrices, mouseX, mouseY, delta);
        //fill(matrices,0,0,this.width,this.height,0xAAFFFFFF);
        RenderSystem.disableBlend();
        matrices.pop();
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void resize(MinecraftClient client, int w, int height) {
        this.name.setWidth(w);
        this.uuid.setWidth(w);
        this.id.setWidth(w);
        this.session.setWidth(w);
        super.resize(client, w, height);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.save.mouseClicked(mouseX,mouseY,button);
        this.exit.mouseClicked(mouseX, mouseY, button);
        this.name.mouseClicked(mouseX, mouseY, button);
        this.uuid.mouseClicked(mouseX, mouseY, button);
        this.id.mouseClicked(mouseX, mouseY, button);
        this.session.mouseClicked(mouseX, mouseY, button);
        return false;
    }

    @Override
    public boolean charTyped(char chr, int keyCode) {
        this.name.charTyped(chr, keyCode);
        this.uuid.charTyped(chr, keyCode);
        this.id.charTyped(chr, keyCode);
        this.session.charTyped(chr, keyCode);
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        this.name.keyPressed(keyCode, scanCode, modifiers);
        this.uuid.keyPressed(keyCode, scanCode, modifiers);
        this.id.keyPressed(keyCode, scanCode, modifiers);
        this.session.keyPressed(keyCode, scanCode, modifiers);
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        this.name.keyReleased(keyCode, scanCode, modifiers);
        this.uuid.keyReleased(keyCode, scanCode, modifiers);
        this.id.keyReleased(keyCode, scanCode, modifiers);
        this.session.keyReleased(keyCode, scanCode, modifiers);
        return false;
    }
}

