import {useState} from "react";

const Page2 = () => {
  const [file, setFile] = useState(null);
  const [uploadProgress, setUploadProgress] = useState(0);
  const CHUNK_SIZE = 5 * 1024 * 1024; // 5MB 단위로 분할

  const handleFileChange = (event) => {
    setFile(event.target.files[0]);
  };

  const uploadChunk = async (chunkFile, originalFilename, chunkIndex, lastChunkIndex) => {

    try {
      const response = await fetch("/api/upload/chunk", {
        method: "POST",
        headers: {
          "Content-Type": "application/octet-stream",
          "X-Original-Filename": encodeURIComponent(originalFilename),
          "X-Chunk-Index": chunkIndex,
          "X-Last-Chunk-Index": lastChunkIndex,
        },
        body: chunkFile, // Blob 형태로 전송
      });

      if (!response.ok) {
        throw new Error("Chunk upload failed");
      }
    } catch (error) {
      console.error("Upload error:", error);
    }
  };

  const handleUpload = async () => {
    console.time('calculatingTime')

    if (!file) {
      alert("파일을 선택하세요.");
      return;
    }

    const fileSize = file.size;
    let uploadedSize = 0;

    let lastChunkIndex = 0;
    let chunkIndex = 0;
    for (let start = 0; start < fileSize; start += CHUNK_SIZE) {
      const end = Math.min(start + CHUNK_SIZE, fileSize);
      const chunk = file.slice(start, end);
      //const isLastChunk = end >= fileSize;

      if (end >= fileSize) {
        lastChunkIndex = chunkIndex;
      }

      await uploadChunk(chunk, file.name, chunkIndex);

      uploadedSize = end;
      chunkIndex++;

      setUploadProgress(((uploadedSize / fileSize) * 99).toFixed(2));
    }

    const response = await fetch("/api/upload/chunk/complete", {
      method: "POST",
      headers: {
        "X-Original-Filename": encodeURIComponent(file.name),
        "X-Last-Chunk-Index": lastChunkIndex,
      },
    });

    console.log("response : ", response);

    setUploadProgress(100);
    console.timeEnd('calculatingTime');
  };


  return (
    <div className="App">
      Page 2

      <hr/>

      <div className="p-4 max-w-md mx-auto">
        <h2 className="text-xl font-bold mb-4">Chunked File Upload</h2>
        <input type="file" onChange={handleFileChange} className="mb-4"/>
        <button
          onClick={handleUpload}
          className="bg-blue-500 text-white px-4 py-2 rounded"
        >
          업로드
        </button>
        {uploadProgress > 0 && <p className="mt-2">진행률: {uploadProgress}%</p>}
      </div>
    </div>
  );
}

export default Page2;
