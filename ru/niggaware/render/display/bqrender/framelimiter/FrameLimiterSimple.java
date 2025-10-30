package ru.niggaware.render.display.bqrender.framelimiter;

public class FrameLimiterSimple extends AbstractFrameLimiter {
    
    public FrameLimiterSimple(int targetFPS) {
        super(targetFPS);
    }
    
    @Override
    public void limitFrame() {
        long currentTime = System.nanoTime();
        long deltaTime = currentTime - lastFrameTime;
        
        if (deltaTime < frameTime) {
            try {
                Thread.sleep((frameTime - deltaTime) / 1000000L); // Convert nanoseconds to milliseconds
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        lastFrameTime = System.nanoTime();
    }
}
