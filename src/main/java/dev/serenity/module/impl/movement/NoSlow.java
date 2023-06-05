package dev.serenity.module.impl.movement;

import dev.serenity.event.impl.PostMotionEvent;
import dev.serenity.event.impl.PreMotionEvent;
import dev.serenity.event.impl.SlowDownEvent;
import dev.serenity.module.Category;
import dev.serenity.module.Module;
import dev.serenity.module.impl.combat.KillAura;
import dev.serenity.utilities.player.PacketUtils;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.lwjgl.input.Keyboard;

public class NoSlow extends Module {

    public NoSlow() {
        super("NoSlow", "Makes you move with full speed when using items", Category.MOVEMENT, Keyboard.KEY_NONE, true);
    }

    @Override
    public void onSlowDown(SlowDownEvent event) {
        event.setForward(1);
        event.setStrafe(1);
    }
}
