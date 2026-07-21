import axios from "axios";

// Registers on axios's shared default instance, so every existing
// axios.get/post/put/delete call across the app picks up the token
// without needing to import a separate client.
axios.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});
