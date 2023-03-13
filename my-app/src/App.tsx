import React from 'react';
//import logo from './logo.svg';
import './App.css';
import Navbar from './components/tailwind/Navbars';
import Home from './components/home';
import Pagination from './components/tailwind/Pagination';

function App() {
  return (
    <>
    <Navbar/>
    
    <h1 className="text-3xl font-bold underline"> Hello world! </h1>

    <Home/>
    {/* <Pagination/> */}
    </>
  )
}

export default App;