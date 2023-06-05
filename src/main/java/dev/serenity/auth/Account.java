package dev.serenity.auth;

import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public class Account {
    private String email;
    private String passworld;
    private MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();

    public Account(String email, String passworld) {
        this.email = email;
        this.passworld = passworld;
    }

    public void login() {
        try {
            MicrosoftAuthResult result = authenticator.loginWithCredentials(email, passworld);
            Minecraft.getMinecraft().session = new Session(result.getProfile().getName(), result.getProfile().getId(), result.getAccessToken(), "legacy");
        } catch (MicrosoftAuthenticationException e) {
            throw new RuntimeException(e);
        }
    }

    public String getEmail() {
        return email;
    }

    public String getPassworld() {
        return passworld;
    }
}
