import onnx
import caffe2.python.onnx.backend
# Prepare the inputs, here we use numpy to generate some random inputs for demo purpose
import numpy as np
def run_shit():
    img = np.random.randn(1, 3, 224, 224).astype(np.float32)
    # Load the ONNX model
    model = onnx.load('squeezenet.onnx')
    # Run the ONNX model with Caffe2
    outputs = caffe2.python.onnx.backend.run_model(model, [img])
    return 1