import { useEffect, useState } from 'react'
import './App.css'
import axios from './api'
import GlobalInfo from './components/GlobalInfo'
import Map from './components/Map'
import SpainTable from './components/SpainTable'
import { Card, CardContent, CircularProgress } from '@material-ui/core'
import AutonomyStats from './components/AutonomyStats'
import ProvinceStats from './components/ProvinceStats'
import { sortData } from './utils';
import Top3 from './components/Top3'
import TopDateBy from './components/TopDateBy'
import DaysWithMoreThan from './components/DaysWithMoreThan'

function App() {
  const [loading, setLoading] = useState(true);
  const [dataToday, setDataToday] = useState();
  const [dataDate, setDataDate] = useState();
  const [inputDate, setInputDate] = useState(null);
  const [errorDate, setErrorDate] = useState("");
  const [totalGlobalInfo, setTotalGlobalInfo] = useState({});
  const [globalInfo, setGlobalInfo] = useState({});
  const [globalInfoByDate, setGlobalInfoByDate] = useState();
  const [tableInfo, setTableInfo] = useState([]);
  const [selectedProvince, setSelectedProvince] = useState("Sevilla");
  const [topTitle, setTopTitle] = useState("Nuevos confirmados");

  useEffect(() => {
    const date = new Date(new Date().getTime() - 86400000).toISOString();
    const today = date.slice(0, 10);

    const getDataApiToday = () => {
      axios
        .get("/dateData?date=" + today)
        .then((res) => {
          setDataToday(res.data);
          setDataDate(res.data);
          setGlobalInfo({
            dateUpdate: res.data.date,
            
            newConfirmed: res.data.newConfirmed,
            newRecovered: res.data.newRecovered,
            newDeaths: res.data.newDeaths,
          });
          let autonomiesData = res.data.autonomies.map((autonomy) => ({
            name: autonomy.name,
            totalCases: autonomy.confirmed,
          }));
          let autonomiesSortedData = sortData(autonomiesData);
          setTableInfo(autonomiesSortedData);
          setLoading(false);
        })
        .catch((err) => {
          console.log(err);
        });
      axios.get("/totalData")
      .then((res) => {
        setTotalGlobalInfo({
          confirmed: res.data.confirmed,
          recovered: res.data.recovered,
          deaths: res.data.deaths,
        });
      })
      .catch((err) =>{
        console.log(err);
      })

    };
    getDataApiToday();
  }, []);

  const handleChangeDate = (e) => {
    e.preventDefault();
    setInputDate(e.target.value);
    if (new Date(e.target.value).getTime() < new Date("03/01/2020").getTime()) {
      setErrorDate("La fecha debe ser mayor al 1 de marzo de 2020");
      setTimeout(() => {
        setErrorDate("");
        setInputDate("");
      }, 2000);
    } else if (Math.floor(new Date(e.target.value).getTime() /1000 /60 /60 /24) >= Math.floor(new Date().getTime() /1000 /60 /60 /24)) {
      setErrorDate("La fecha debe ser menor que la de hoy");
      setTimeout(() => {
        setErrorDate("");
        setInputDate("");
      }, 2000);
    } else {
      axios
        .get("/dateData?date=" + e.target.value)
        .then((res) => {
          setDataDate(res.data);
          setGlobalInfoByDate({
            dateUpdate: res.data.date,
            confirmed: res.data.confirmed,
            newConfirmed: res.data.newConfirmed,
            recovered: res.data.recovered,
            newRecovered: res.data.newRecovered,
            deaths: res.data.deaths,
            newDeaths: res.data.newDeaths,
          });
        })
        .catch((err) => {
          console.log(err);
        });
    }
  };

  return (
    <div className="app">
      <div className="app-left">
        <div className="app-header">
          <h1>Tracker Covid-19 Espa??a</h1>
          <div className="input-container">
            <h3>Proporciona una fecha</h3>
            <input
              className="date-input"
              type="date"
              value={inputDate}
              onChange={handleChangeDate}
            />
            {errorDate !== "" ? <p className="error-date">{errorDate}</p> : ""}
          </div>
        </div>
        {loading ? (
          <CircularProgress />
        ) : (
          <>
            {globalInfo && (
              <h3 className="date-update">
                Datos Nacionales actualizados el {globalInfo.dateUpdate}
              </h3>
            )}
            <div className="global-stats">
              <GlobalInfo
                title="Casos Coronavirus"
                cases={globalInfo.newConfirmed}
                total={totalGlobalInfo.confirmed}
                type="cases"
              />
              <GlobalInfo
                title="Recuperados"
                cases={globalInfo.newRecovered}
                total={totalGlobalInfo.recovered}
                type="recovered"
              />
              <GlobalInfo
                title="Muertes"
                cases={globalInfo.newDeaths}
                total={totalGlobalInfo.deaths}
                type="deaths"
              />
            </div>
            {!globalInfoByDate ? (
              ""
            ) : (
              <>
                <h3 className="date-update">
                  Datos nacionales para la fecha: {globalInfoByDate.dateUpdate}
                </h3>
                <div className="global-stats">
                  <GlobalInfo
                    title="Casos Coronavirus"
                    cases={globalInfoByDate.newConfirmed}
                    total={globalInfoByDate.confirmed}
                    type="cases"
                  />
                  <GlobalInfo
                    title="Recuperados"
                    cases={globalInfoByDate.newRecovered}
                    total={globalInfoByDate.recovered}
                    type="recovered"
                  />
                  <GlobalInfo
                    title="Muertes"
                    cases={globalInfoByDate.newDeaths}
                    total={globalInfoByDate.deaths}
                    type="deaths"
                  />
                </div>
              </>
            )}
            <Map
              totalData={dataDate}
              setSelectedProvince={setSelectedProvince}
            />
            {dataToday.date !== dataDate.date ? (
              <div className="selected-stats">
                <AutonomyStats
                  dataDate={dataToday}
                  province={selectedProvince}
                />
                <ProvinceStats
                  dataDate={dataToday}
                  province={selectedProvince}
                />
              </div>
            ) : (
              ""
            )}
            <div className="selected-stats">
              <AutonomyStats dataDate={dataDate} province={selectedProvince} />
              <ProvinceStats dataDate={dataDate} province={selectedProvince} />
            </div>
          </>
        )}
      </div>
    {loading ? (
        <CircularProgress />
      ) : ( 
        <Card className="app-right">
          <CardContent>
            <h3>Casos en Espa??a por Comunidad</h3>
            <SpainTable info={tableInfo} />
            <h3 className="title-top3">Top 3 provincias con nuevos casos</h3>
            <Top3/>
            <h3 className="title-top3">Fecha donde hubo m??s {topTitle}</h3>
            <TopDateBy topTitle={topTitle} setTopTitle={setTopTitle}/>
            <DaysWithMoreThan/>
          </CardContent>
        </Card>
      )}
    </div>
  );
}

export default App
