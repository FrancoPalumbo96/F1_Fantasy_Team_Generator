const fetch = require("node-fetch");
var fs = require('fs');
const url = 'https://fantasy-api.formula1.com/partner_games/f1/players'
let saveData = []

fetch(url)
    .then(response => response.json())
    .then(data => {
        //console.log(data)
        const playerData = data.players;
        for (let i = 0; i < playerData.length; i++){
            const player = playerData[i];
            if(player.position === 'Constructor'){
                saveData.push({
                    "constructor": {
                        "name": player.known_name,
                        "points": player.season_score,
                        "price": player.price
                    }
                })
            } else if(player.position === 'Driver'){
                saveData.push({
                    "pilot": {
                        "name": player.display_name,
                        "points": player.season_score,
                        "price": player.price
                    }
                })
            }
        }
    })
    .catch(err => {
        console.log(err)
    })
    .then(() => {
        try{
            const path = "D:\\sandbox\\f1-fantasy\\src\\main\\resources\\data.json";
            const stringifyData = JSON.stringify(saveData, null, 2)
            console.log("Saved Data: ")
            console.log(stringifyData)
            fs.writeFileSync(path, stringifyData);
        } catch (error){
            console.log(error)
        }

    });
