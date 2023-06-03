package dev.serenity.utilities.other;

public class HoveringUtils {
    public static boolean isHovering(int mouseX, int mouseY, float left, float top, float right, float bottom) {
        return (mouseX >= left && mouseX <= right && mouseY >= top && mouseY <= bottom);
    }
}
