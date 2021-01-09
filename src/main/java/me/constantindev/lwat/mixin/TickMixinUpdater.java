package me.constantindev.lwat.mixin;

import me.constantindev.lwat.Config;
import me.constantindev.lwat.LoginWithAccessToken;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Session;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class TickMixinUpdater {
    @Mutable @Shadow @Final private Session session;

    @Inject(at=@At("HEAD"),method="tick")
    public void tick(CallbackInfo ci) {
        if (Config.newsession != null) {
            LoginWithAccessToken.log(Level.INFO,"Setting new session");
            LoginWithAccessToken.log(Level.INFO,this.session.getUsername() + " -> " + Config.newsession.getUsername());
            LoginWithAccessToken.log(Level.INFO,this.session.getAccessToken() + " -> " + Config.newsession.getAccessToken());
            LoginWithAccessToken.log(Level.INFO,this.session.getSessionId() + " -> " + Config.newsession.getSessionId());
            LoginWithAccessToken.log(Level.INFO,this.session.getUuid() + " -> " + Config.newsession.getUuid());
            this.session = Config.newsession;

            Config.newsession = null;
        }
    }
}
