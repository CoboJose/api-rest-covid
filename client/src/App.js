import { useState } from 'react'
import './App.css'
import axios from './api'
import { SVGMap } from "react-svg-map"
import spain3 from "./map"
import {getLocationName} from "./utils"
import "react-svg-map/lib/index.css";

function App() {

  const [models, setModels] = useState(null)
  const [pointedLocation, setPointedLocation] = useState(null)
  const [selectedLocation, setSelectedLocation] = useState(null)

  const getModels = () => {
    axios.get("/models")
    .then(res => {
      setModels(res.data.models)
    })
    .catch(err => {
      console.log(err.response.data)
    })
  }
  
  const handleLocationMouseOver = (event) => {
		const pointedLocation = getLocationName(event);
		 setPointedLocation(pointedLocation);
	}

  const handleOnChange = (selectedNode) => {
		setSelectedLocation(prevState => {
			return {
				...prevState,
				selectedLocation: selectedNode.attributes.name.value
			};
		});
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
      <SVGMap map={spain3} role="radiogroup" onLocationMouseOver={handleLocationMouseOver} onChange={handleOnChange}/> 
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
