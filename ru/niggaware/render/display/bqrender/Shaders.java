package ru.niggaware.render.display.bqrender;

import ru.niggaware.render.display.bqrender.impl.*;

public class Shaders {
    
    public static void init() {
        BloomShader.init();
        BlurShader.init();
        KawaseBloomDown.init();
        KawaseBloomUp.init();
        KawaseBlurDown.init();
        KawaseBlurUp.init();
    }
    
    public static void cleanup() {
        BloomShader.cleanup();
        BlurShader.cleanup();
        KawaseBloomDown.cleanup();
        KawaseBloomUp.cleanup();
        KawaseBlurDown.cleanup();
        KawaseBlurUp.cleanup();
    }
}
