import { useEffect, useState } from "react";
import { getDocuments, createDocument } from "./services/documentService";

function App() {
  const [documents, setDocuments] = useState([]);
  const [fileName, setFileName] = useState("");

  useEffect(() => {
    getDocuments()
      .then(response => {
        setDocuments(response.data);
      })
      .catch(error => {
        console.error(error);
      });
  }, []);

  const handleCreateDocument = () => {
    createDocument({
      fileName: fileName
  })
  .then(response => {
    console.log(response.data);
  })
  .catch(error => {
    console.error(error);
  })
  };

  return (
    <div>
      <h1>Supplier Document Platform</h1>

      <input
        type="text"
        value={fileName}
        onChange={(e) => setFileName(e.target.value)}
        placeholder="Enter filename"
      />
      <button onClick={handleCreateDocument}>
        Create Document
      </button>
      {documents.map(doc => (
        <div key={doc.id}>
          {doc.fileName}
        </div>
      ))}
    </div>
  );
}

export default App;