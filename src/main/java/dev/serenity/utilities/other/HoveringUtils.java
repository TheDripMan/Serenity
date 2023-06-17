package dev.serenity.utilities.other;

public class HoveringUtils {
    public static boolean isHovering(int mouseX, int mouseY, float x, float y, float x2, float y2) {
        return (mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2);
    }
}
