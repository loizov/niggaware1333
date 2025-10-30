package ru.niggaware.render.display.bqrender.framelimiter;

public abstract class AbstractFrameLimiter implements IFrameCall {
    protected int targetFPS;
    protected long lastFrameTime;
    protected long frameTime;
    
    public AbstractFrameLimiter(int targetFPS) {
        this.targetFPS = targetFPS;
        this.frameTime = 1000000000L / targetFPS; // nanoseconds per frame
        this.lastFrameTime = System.nanoTime();
    }
    
    public abstract void limitFrame();
    
    public void setTargetFPS(int fps) {
        this.targetFPS = fps;
        this.frameTime = 1000000000L / fps;
    }
    
    public int getTargetFPS() {
        return targetFPS;
    }
}
