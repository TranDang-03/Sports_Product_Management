// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries
import { getAuth } from "firebase/auth";
import { getFirestore } from "firebase/firestore";
import { getStorage } from "firebase/storage";

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyD8LcBpPhzrrHRAY7bqJPa6bic84yzGkLU",
  authDomain: "thatbluestorehaveshoe-83fb8.firebaseapp.com",
  projectId: "thatbluestorehaveshoe-83fb8",
  storageBucket: "thatbluestorehaveshoe-83fb8.appspot.com",
  messagingSenderId: "595922304626",
  appId: "1:595922304626:web:fc75c7e05a2495263fcceb",
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
export const auth = getAuth(app);
export const db = getFirestore(app);
export const storage = getStorage(app);
