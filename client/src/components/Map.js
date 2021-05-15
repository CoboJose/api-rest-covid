import React, {useState} from 'react'
import spain3 from "../resources/map"
import {getLocationName} from "../utils"
import {SVGMap} from "react-svg-map"
import "../style/Map.css"

function Map() {
    const [pointedLocation, setPointedLocation] = useState(null)
    const [selectedLocation, setSelectedLocation] = useState(null)
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
    return (
        <div className="map">
            <SVGMap
                map={spain3}
                role="radiogroup"
                onLocationMouseOver={handleLocationMouseOver}
                onChange={handleOnChange}
            />
        </div>
    )
}

export default Map