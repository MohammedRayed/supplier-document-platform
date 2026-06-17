import axios from "axios";

const API_URL = "http://localhost:8080/documents";

export const getDocuments = () => {
  return axios.get(API_URL);
};

export const createDocument = (document) => {
  return axios.post(API_URL, document);
};