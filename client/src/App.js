import { useState } from 'react'
import './App.css'
import axios from './api'
import "react-svg-map/lib/index.css";
import GlobalInfo from './components/GlobalInfo'
import Map from './components/Map'
import SpainTable from './components/SpainTable'
import { Card, CardContent, IconButton } from '@material-ui/core'
import AutonomyStats from './components/AutonomyStats'
import ProvinceStats from './components/ProvinceStats'
import DateRangeIcon from '@material-ui/icons/DateRange';

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
    <div className="app">
      <div className="app-left">
        <div className="app-header">
          <h1>Tracker Covid-19 España</h1>
          <div className="input-container">
            <h3>Proporciona una fecha</h3>
            <input
              className="date-input"
              type="date"
            /> 
          </div>
        </div>
        <div className="global-stats">
          <GlobalInfo title="Casos Coronavirus" cases={2000} total={29837461} type="cases" />
          <GlobalInfo title="Recuperados" cases={4000} total={134123} type="recovered"/>
          <GlobalInfo title="Muertes" cases={6000} total={12345} type="deaths"/>
        </div>
        <Map />
        <div className="selected-stats">
          <AutonomyStats title="Andalucía" cases={23} recovered={234} deaths={2144} />
          <ProvinceStats title="Sevilla" cases={23} recovered={234} deaths={2144} />
        </div>
      </div>
      <Card className="app-right">
        <CardContent>
          <h3>Casos en España por provincia</h3>
          <SpainTable />
          <h3>Top 3 provincias</h3>
        </CardContent>
      </Card>
      {/* 
        <button onClick={() => getModels()}>Get models</button>
        <br /> */}

      {/* {models &&
          models.map((model) => (
            <div key={model.name}>
              <p>Name: {model.name}</p>
              <p>Description: {model.description}</p>
              <br />
            </div>
          ))}

        <br />
        <button onClick={() => postModel()}>Post Model</button>
        <br /> */}
    </div>
  );
}

export default App
