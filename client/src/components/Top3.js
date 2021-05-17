import axios from "../api"
import React, { useEffect, useState } from 'react'
import {Bar} from "react-chartjs-2"

function Top3() {
    const [top3DataLabel, setTop3DataLabel] = useState([]);
    const [top3Data, setTop3Data] = useState([]);

    useEffect(() => {
        const getTop3Data = () => {
            axios.get("top3Provinces")
            .then((res) => {
                let nameLabel = [];
                let dataLabel = []; 
                res.data.map((province) => {
                    nameLabel.push(province.name)
                    dataLabel.push(province.newConfirmed)
                });
                
                setTop3DataLabel(nameLabel);
                setTop3Data(dataLabel)
            })
            .catch(err => {
                console.log(err)
            })
        };
        getTop3Data();
    }, [])

    const data = {
        labels: top3DataLabel,
        datasets: [
          {
            label: 'Nuevos casos',
            data: top3Data,
            backgroundColor: [
              'rgba(255, 99, 132, 0.2)',
              'rgba(54, 162, 235, 0.2)',
              'rgba(255, 206, 86, 0.2)',
            ],
            borderColor: [
              'rgba(255, 99, 132, 1)',
              'rgba(54, 162, 235, 1)',
              'rgba(255, 206, 86, 1)',
            ],
            borderWidth: 1,
          },
        ],
      };

    const options = {
        scales: {
            yAxes: [
              {
                ticks: {
                  beginAtZero: true,
                },
              },
            ],
          },
    };

    
      
    return (
        <div>
            <Bar data={data} options={options}/>
        </div>
    )
}

export default React.memo(Top3, () => true)
    