import {useState} from "react";

const Page1 = () => {

  const [file, setFile] = useState(null);
  const [uploadStatus, setUploadStatus] = useState("");

  const handleFileChange = (event) => {
    const file = event.target.files[0];
    console.log("file : ", file.name)
    setFile(file);
  };

  const handleUpload = async () => {
    if (!file) {
      setUploadStatus("파일을 선택하세요.");
      return;
    }

    setUploadStatus("업로드 중...");
    console.log("file.name ", file.name);
    try {
      const response = await fetch("/api/upload/simple", {
        method: "POST",
        headers: {
          "Content-Type": "application/octet-stream",
          "X-Original-Filename": encodeURIComponent(file.name),
        },
        body: file, // Blob 형태로 전송
      });

      const result = await response.text();
      setUploadStatus(`업로드 완료: ${result}`);
    } catch (error) {
      console.error("Upload failed:", error);
      setUploadStatus("업로드 실패");
    }
  };

  return (
    <div className="App">

      <div className="p-4 max-w-md mx-auto">
        <h2 className="text-xl font-bold mb-4">파일 업로드</h2>
        <input type="file" onChange={handleFileChange} className="mb-4"/>
        <button
          onClick={handleUpload}
          className="bg-blue-500 text-white px-4 py-2 rounded"
        >
          업로드
        </button>
        {uploadStatus && <p className="mt-2 text-gray-700">{uploadStatus}</p>}
      </div>
    </div>
  );
}

export default Page1;
