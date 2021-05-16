import React, {useEffect, useState} from 'react'
import spain3 from "../resources/map"
import {getLocationName} from "../utils"
import {SVGMap} from "react-svg-map"
import "../style/Map.css"

function Map({totalData, setSelectedProvince}) {
    const [pointedLocation, setPointedLocation] = useState(null)
    const [selectedLocation, setSelectedLocation] = useState(null)
    const totalDataMap = totalData;
    const [provincesCases, setProvincesCases] = useState([])
    const [maxCase, setMaxCase] = useState(null)

    useEffect(() => {
       
        const getProvincesCases = () => {
            let provincesData = [];
            let max = 0;
            for(var autonomy of totalDataMap.autonomies){
                for (var province of autonomy.provinces){
                    provincesData.push({
                        name: province.name,
                        cases: province.newConfirmed
                    })

                    if(max < province.newConfirmed) {
                        max = province.newConfirmed;
                    }
                    
                }
            }
            setMaxCase(max);
            setProvincesCases(provincesData);
            
        }
        getProvincesCases();

    },[totalData])
    
    
    const handleLocationMouseOver = (event) => {
		const pointedLocation = getLocationName(event);
		 setPointedLocation(pointedLocation);
	}

    const handleOnChange = (selectedNode) => {
		setSelectedLocation(selectedNode.target.attributes.name.value);
        setSelectedProvince(selectedNode.target.attributes.name.value);
	}

    const generateColor = (location, index) =>{
        const provinceCases = provincesCases.find(e => e.name == location.name)
        let colorDivision = null;

        if(provinceCases != undefined ){
            if(provinceCases.cases <= maxCase/6){
                colorDivision=1;
            }else if(provinceCases.cases <= maxCase/5){
                colorDivision=2
            }else if(provinceCases.cases <= maxCase/3){
                colorDivision=3
            }else{
                colorDivision=4
            }
        }
        return `svg-map__location color-${colorDivision}`;
    }

    return (
        <div className="map">
            <SVGMap
                map={spain3}
                role="radiogroup"
                onLocationMouseOver={handleLocationMouseOver}
                locationClassName={generateColor}
                onLocationClick={handleOnChange}
            />
        </div>
    )
}

export default Map