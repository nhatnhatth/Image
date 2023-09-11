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
                    "void main()\n" +
                    "{\n" +
                    "  gl_Position = position;\n" +
                    "  Vertex_UV = inputTextureCoordinate;\n" +
                    "    textureCoordinate = inputTextureCoordinate.xy;\n" +
                    "}";

    public static final String FISHEYE_FRAGMENT_SHADER =
            "uniform sampler2D tex0;\n" +
                    "varying vec4 Vertex_UV;\n" +
                    "const float PI = 3.1415926535;\n" +
                    "\n" +
                    "void main()\n" +
                    "{\n" +
                    "  float aperture = 178.0;\n" +
                    "  float apertureHalf = 0.5 * aperture * (PI / 180.0);\n" +
                    "  float maxFactor = sin(apertureHalf);\n" +
                    "  \n" +
                    "  vec2 uv;\n" +
                    "  vec2 xy = 2.0 * Vertex_UV.xy - 1.0;\n" +
                    "  float d = length(xy);\n" +
                    "  if (d < 0.5)\n" +
                    "  {\n" +
                    "    d = length(xy);\n" +
                    "    float z = sqrt(1.0 - d * d);\n" +
                    "    float r = atan(d, z) / PI;\n" +
                    "    float phi = atan(xy.y, xy.x);\n" +
                    "    \n" +
                    "    uv.x = 0.4 * r * cos(phi) + 0.5;\n" +
                    "    uv.y = 0.4 * r * sin(phi) + 0.5;\n" +
                    "  }\n" +
                    "  else\n" +
                    "  {\n" +
                    "    uv = Vertex_UV.xy;\n" +
                    "  }\n" +
                    "  vec4 c = texture2D(tex0, uv);\n" +
                    "  gl_FragColor = c;\n" +
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
