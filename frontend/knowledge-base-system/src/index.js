import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import {
  BrowserRouter,
  Routes,
  Route
} from "react-router-dom";
import CreateQuestion from './components/CreateQuestion'
import QuestionList from './components/QuestionList'
import QuestionDetail from './components/QuestionDetail'

ReactDOM.render(
  <BrowserRouter>
    <Routes>
      <Route path="/" element={<App />} />
      <Route path='QuestionList' element={<QuestionList/>} />
      <Route path='CreateQuestion' element={<CreateQuestion/>} />
      <Route path='QuestionDetail/:id' element={<QuestionDetail/>} />
    </Routes>
  </BrowserRouter>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
