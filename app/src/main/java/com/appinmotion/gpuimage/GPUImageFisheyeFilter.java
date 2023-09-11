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
public class GPUImageFisheyeFilter extends GPUImageFilter {
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
                    "varying vec2 textureCoordinate;\n" +
                    "const float PI = 3.1415926535;\n" +
                    "uniform float BarrelPower;\n" +
                    "\n" +
                    "vec2 Distort(vec2 p)\n" +
                    "{\n" +
                    "    float theta  = atan(p.y, p.x);\n" +
                    "    float radius = length(p);\n" +
//                    "    if(radius < 0.05)" +
//                    "   {" +
//                    "       radius = pow(radius, 1.4);\n" +
//                    "       p.x = radius * cos(theta);\n" +
//                    "       p.y = radius * sin(theta);\n" +
//                    "       return 0.5 * (p + 1.0);" +
//
//                    "   }" +
                    "    radius = pow(radius, 2.0);\n" +
                    "    p.x = radius * cos(theta);\n" +
                    "    p.y = radius * sin(theta);\n" +
                    "    return 0.5 * (p + 1.0);\n" +
                    "}\n" +
                    "\n" +
                    "void main()\n" +
                    "{\n" +
                    "  vec2 xy = 2.0 * Vertex_UV.xy - 1.0;\n" +
                    "  vec2 uv;\n" +
                    "  float d = length(xy);\n" +
                    "  if (d < 1.0)\n" +
                    "  {\n" +
                    "    uv = Distort(xy);\n" +
                    "  }\n" +
                    "  else\n" +
                    "  {\n" +
                    "    uv = Vertex_UV.xy;\n" +
                    "  }\n" +
                    "  vec4 c = texture2D(tex0, uv);\n" +
                    "  gl_FragColor = c;\n" +
                    "}";


    public GPUImageFisheyeFilter() {
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
