#version 150

// shader experiment, this doesn't work

#moj_import <water_fog.glsl>

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;
uniform float u_time;

in float vertexDistance;
in vec4 vertexColor;
in vec2 texCoord0;

out vec4 fragColor;

void main() {
    vec3 rainbow_color = vec3(sin(0.3 * u_time), sin(0.2 * u_time), sin(0.8 * u_time));
    // vec4 color = texture(Sampler0, texCoord0) * vertexColor * ColorModulator;
    vec4 color = vec4(rainbow_color, 0.0);
    fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}
