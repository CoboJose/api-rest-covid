import React from 'react'
import "../style/SpainTable.css"

function SpainTable({info}) {
    return (
        <div className = "table">
            {info.map(autonomy => (
                <tr>
                    <td>
                        {autonomy.name}
                    </td>
                    <td>
                        <strong>{autonomy.totalCases}</strong>
                    </td>
                </tr>
            ))}
        </div>
    )
}

export default SpainTable