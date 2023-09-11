/*
 * Copyright (C) 2018 CyberAgent, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appinmotion.gpuimage;

import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;

/**
 * Sharpens the picture. <br>
 * <br>
 * sharpness: from -4.0 to 4.0, with 0.0 as the normal level
 */
public class GPUImageBarrelFilter extends GPUImageFilter {
    public static final String FISHEYE_VERTEX_SHADER =
            "varying vec4 Vertex_UV;\n" +
                    "attribute vec4 position;\n" +
                    "attribute vec4 inputTextureCoordinate;\n" +
                    "attribute vec2 a_Coordinate;\n" +
                    " \n" +
                    "varying vec2 textureCoordinate;\n" +
                    " \n" +
                    "uniform mat4 gxl3d_ModelViewProjectionMatrix;\n" +
                    "vec4 Distort(vec4 p)\n" +
                    "{\n" +
                    "    vec2 v = p.xy / p.w;\n" +
                    "    float radius = length(v);\n" +
                    "    if (radius > 0.0)\n" +
                    "    {\n" +
                    "      float theta = atan(v.y,v.x);\n" +
                    "      radius = pow(radius, 1.4);\n" +
                    "      v.x = radius * cos(theta);\n" +
                    "      v.y = radius * sin(theta);\n" +
                    "      p.xy = v.xy * p.w;\n" +
                    "    }\n" +
                    "    return p;\n" +
                    "}"+
                    "void main()\n" +
                    "{\n" +
                    "  gl_Position = Distort(position);\n" +
                    "  Vertex_UV = inputTextureCoordinate;\n" +
                    "  textureCoordinate = inputTextureCoordinate.xy;\n" +
                    "}";

    public static final String FISHEYE_FRAGMENT_SHADER =
            "uniform sampler2D tex0;\n" +
            "varying vec4 Vertex_UV;\n" +
            "varying vec2 textureCoordinate;\n" +
            "void main()\n" +
            "{\n" +
            "   vec4 c = vec4(1.0);\n" +
            "   vec2 uv = Vertex_UV.xy;\n" +
            "   c = texture2D(tex0, uv);\n" +
            "   gl_FragColor =  c;"+
            "}";


    public GPUImageBarrelFilter() {
        super(FISHEYE_VERTEX_SHADER, FISHEYE_FRAGMENT_SHADER);
    }

    @Override
    public void onInit() {
        super.onInit();
    }

    @Override
    public void onInitialized() {
        super.onInitialized();
    }

    @Override
    public void onOutputSizeChanged(final int width, final int height) {
        super.onOutputSizeChanged(width, height);
    }
}
