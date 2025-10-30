package ru.niggaware.render.display.bqrender.impl;

public class VertexGlsl {
    public static final String VERTEX_SHADER = 
        "#version 150\n" +
        "in vec3 Position;\n" +
        "uniform mat4 ProjMat;\n" +
        "uniform vec2 InSize;\n" +
        "uniform vec2 OutSize;\n" +
        "out vec2 texCoord;\n" +
        "void main() {\n" +
        "    vec4 outPos = ProjMat * vec4(Position, 1.0);\n" +
        "    gl_Position = vec4(outPos.xy, 0.2, 1.0);\n" +
        "    texCoord = Position.xy / OutSize;\n" +
        "}\n";

    public static final String FRAGMENT_SHADER = 
        "#version 150\n" +
        "uniform sampler2D DiffuseSampler;\n" +
        "uniform vec2 InSize;\n" +
        "uniform vec2 OutSize;\n" +
        "in vec2 texCoord;\n" +
        "out vec4 fragColor;\n" +
        "void main() {\n" +
        "    vec4 color = texture(DiffuseSampler, texCoord);\n" +
        "    fragColor = color;\n" +
        "}\n";
}
