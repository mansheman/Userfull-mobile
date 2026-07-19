# Models

Place required model files in this directory.

## Required

| Model | Source | Size |
|---|---|---|
| `inswapper_128.onnx` | [inswapper_128.onnx](https://huggingface.co/ashinsp/FaceFusionModels/resolve/main/inswapper_128.onnx) | 554 MB |
| `buffalo_l/` | Downloaded automatically by insightface on first run, or copy from `~/.insightface/models/buffalo_l/` | ~200 MB |

## Structure

```
models/
├── inswapper_128.onnx       # Face swap model (DirectML GPU)
└── buffalo_l/               # Face detection + recognition models (CPU)
    ├── det_10g.onnx
    ├── 1k3d68.onnx
    ├── 2d106det.onnx
    ├── genderage.onnx
    └── w600k_r50.onnx
```

## Notes

- `inswapper_128.onnx` runs on **DirectML (GPU)** — requires `onnxruntime-directml`.
- `buffalo_l` runs on **CPU** (DirectML crashes on this model).
- The server sets `INSIGHTFACE_HOME` to this directory so buffalo_l is found locally.
