package dev.serenity.ui.mainmenu.alt.auth;

import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public class Account {
    private String name;
    private String email;
    private String password;
    private MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
    private Type type;

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
        this.type = Type.PREMIUM;
    }

    public Account(String name) {
        this.name = name;
        this.type = Type.CRACKED;
    }
    public void login() {
        if (type == Type.PREMIUM) {
            try {
                MicrosoftAuthResult result = authenticator.loginWithCredentials(email, password);
                Minecraft.getMinecraft().session = new Session(result.getProfile().getName(), result.getProfile().getId(), result.getAccessToken(), "legacy");

            } catch (MicrosoftAuthenticationException e) {
                throw new RuntimeException(e);
            }
        } else {
            Minecraft.getMinecraft().session = new Session(this.name, "", "", "legacy");
        }
    }
}
