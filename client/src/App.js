import { useState } from 'react'
import './App.css'
import axios from './api'

function App() {

  const [models, setModels] = useState(null)

  const getModels = () => {
    axios.get("/models")
    .then(res => {
      setModels(res.data.models)
    })
    .catch(err => {
      console.log(err.response.data)
    })
  }

  const postModel = () => {
    let data = {
      "name":"d",
      "description": "desc2",
      "date": 41486464
    }
    axios.post("/models", data)
    .then(res => {
      console.log(res.data)
    })
    .catch(err => {
      console.log(err.response.data)
    })
  }

  return (
    <div className="App">
      <br/>
      <button onClick={() => getModels()}>Get models</button>
      <br/>

      {models && models.map((model) => (
        <div key={model.name}>
          <p>Name: {model.name}</p>
          <p>Description: {model.description}</p>
          <br/>
        </div>
      ))}

      <br/>
      <button onClick={() => postModel()}>Post Model</button>
      <br/>

    </div>
  )
}

export default App
