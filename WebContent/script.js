// boardDetails();
// check();

const X_CLASS = 'x';
const O_CLASS = 'o';
const WINNING_COMBINATIONS = [
    [0, 1, 2],
    [3, 4, 5],
    [6, 7, 8],
    [0, 3, 6],
    [1, 4, 7],
    [2, 5, 8],
    [0, 4, 8],
    [2, 4, 6]
]

const cellElements = document.querySelectorAll('[data-cell]');
const board = document.getElementById('board');
const create = document.getElementById('createGame');
const winningMessageElement = document.getElementById("winningMessage");
const restartButton = document.getElementById('restartButton');
const createButton = document.getElementById('createButton');
const createInput = document.getElementById('createInput');
const winningMessageTextElement = document.querySelector('[data-winning-message-text]')
const waiting = document.getElementById('waiting');
const readyButton = document.getElementById('ready');
const manualRestartButton = document.getElementById('manualRestart');

let xCount = 0;
let oCount = 0;
let boardCheck = [];
let circleTurn;
let gameKey = "";

restartButton.addEventListener('click', resetGame);
createButton.addEventListener('click', createGame);
createInput.addEventListener('keypress', function (event) {
    if (event.key === "Enter") {
        event.preventDefault();
        createButton.click();
    }
});

readyButton.addEventListener('click', resetGame);

manualRestartButton.addEventListener('click', resetGame)

async function createGame() {
    if (document.getElementById('createInput').value != undefined) {
        gameKey = document.getElementById('createInput').value
        const response = await fetch("http://3.210.183.75:8080/tictactoe/tictactoeserver/createGame?key=" + gameKey)
            .catch(error => console.warn(error));
        const resData = await response.text();
        if (resData == 'X') {
            console.log("waiting for other player")
            create.style.display = "none";
            waiting.style.display = "flex";
            checkLoop();
        } if (resData == 'O') {
            console.log("Let the games begin!")
            startGameO();

        } else {
            console.log(resData)
        }
    }
}

function resetGame() {
    fetch("http://3.210.183.75:8080/tictactoe/tictactoeserver/reset?key=" + gameKey)
        .then(() => {
            location.reload();
        })
}

function startGameX() {
    waiting.style.display = "none";
    board.style.display = "grid";
    manualRestartButton.style.display = "flex";
    cellElements.forEach(cell => {
        cell.classList.remove(X_CLASS)
        cell.classList.remove(O_CLASS)
        cell.addEventListener('click', handleClickX)
        cell.addEventListener('click', handleClickX, { once: true })
    })
    circleTurn = false;
    board.classList.add(X_CLASS);
    xCount = 0;
    oCount = 0;
    boardDetails();
    check();
}


function startGameO() {
    create.style.display = "none";
    board.style.display = "grid";
    manualRestartButton.style.display = "flex";
    cellElements.forEach(cell => {
        cell.classList.remove(X_CLASS)
        cell.classList.remove(O_CLASS)
        cell.addEventListener('click', handleClickO)
        cell.addEventListener('click', handleClickO, { once: true })
    })
    circleTurn = false;
    board.classList.add(O_CLASS);
    xCount = 0;
    oCount = 0;
    boardDetails();
    check();
}

async function checkLoop() {
    const response = await fetch("http://3.210.183.75:8080/tictactoe/tictactoeserver/check?key=" + gameKey)
        .catch(error => console.warn(error));
        
    const resData = await response.text();

    if(resData === "true") {
        startGameX();
    } else {
        const sleep = (ms) => new Promise(resolve => setTimeout(resolve, ms));
        async function delayLoop() {
            await sleep(1000);
            checkLoop();
        }
        delayLoop();
    }  
}

async function check() {
    const res = await fetch("http://3.210.183.75:8080/tictactoe/tictactoeserver/check?key=" + gameKey)
    .catch(error => console.warn(error));
    
    const resData = await res.text();
    
    if (gameKey != "" || undefined){
        if (resData == "false") {
            winningMessageElement.classList.add('show');
            winningMessageTextElement.innerText = `Game Over!`;
        } else if (resData == "true") {
            if (checkWin(X_CLASS)) {
                endGame(false, X_CLASS)
                console.log("X is the winner!")
            } else if (checkWin(O_CLASS)) {
                endGame(false, O_CLASS)
                console.log("O is the winner!")
            } else if (isDraw()) {
                endGame(true)
            }
        }
        const sleep = (ms) => new Promise(resolve => setTimeout(resolve, ms));
        async function delayLoop() {
            await sleep(1000);
            check();
        }
        delayLoop();
    } else{
        winningMessageElement.classList.add('show');
        winningMessageTextElement.innerText = `No game found!`;
    }
}

async function handleClickX(e) {
    const cell = e.target;
    const coordinates = cell.id.split(",");
    const y = coordinates[0];
    const x = coordinates[1];
    
    await boardDetails();
    
    const cellId = cell.getAttribute('id2');
    const location = parseInt(cellId);
    
    if (xCount <= oCount) {
        const res = await fetch("http://3.210.183.75:8080/tictactoe/tictactoeserver/move?key=" + gameKey + "&tile=" + X_CLASS + "&y=" + y + "&x=" + x)
            .catch(error => console.warn(error));
		
		const resData = await res.text();
		
		if (resData != "[TAKEN]" && X_CLASS) {
            saveDetails(X_CLASS,location);
            placeMark(cell, X_CLASS);
		} else {
			console.log("The tile is taken!")
		}
    } else {
        console.log("It's not yet your turn!");
    }
}

async function handleClickO(e) {
    const cell = e.target;
    const coordinates = e.target.id.split(",")
    const y = coordinates[0];
    const x = coordinates[1];
    
    await boardDetails();
    
    const cellId = cell.getAttribute('id2');
    const location = parseInt(cellId);
    
    if (xCount > oCount) {
         const res = await fetch("http://3.210.183.75:8080/tictactoe/tictactoeserver/move?key=" + gameKey + "&tile=" + O_CLASS + "&y=" + y + "&x=" + x)
            .catch(error => console.warn(error));

		const resData = await res.text();
            
		if (resData != "[TAKEN]") {
            saveDetails(O_CLASS,location);
            placeMark(cell, O_CLASS);
        } else {
			console.log("The tile is taken!")
		}
	} else {
        console.log("It's not yet your turn!")
    }
}

function endGame(draw, currentClass) {
    if (draw) {
        winningMessageTextElement.innerText = "Draw!"
        viewHistory(gameKey + X_CLASS);
        viewHistory(gameKey + O_CLASS);
    } else {
        winningMessageTextElement.innerText = `${currentClass.toUpperCase()} Wins!`
        viewHistory(gameKey + X_CLASS);
        viewHistory(gameKey + O_CLASS);
    }
    winningMessageElement.classList.add('show')
}

function isDraw() {
    return [...cellElements].every(cell => {
        return cell.classList.contains(X_CLASS) || cell.classList.contains(O_CLASS)
    })
}

function placeMark(cell, currentClass) {
    cell.classList.add(currentClass)
}

function checkWin(currentClass) {
    return WINNING_COMBINATIONS.some(combination => {
        return combination.every(index => {
            return cellElements[index].classList.contains(currentClass)
        })
    })
}

async function boardDetails() {
    const response = await fetch("http://3.210.183.75:8080/tictactoe/tictactoeserver/board?key=" + gameKey)
    .catch(error => console.warn(error));
    
    const resData = await response.text();

    xCount = 0;
    oCount = 0;
    boardCheck = resData.split(":");
    let i = 0;

    do {
        if (boardCheck?.[i] == 'x') {
            cellElements[i].classList.add('x')
            xCount++;
        } else if (boardCheck?.[i] == 'o') {
            cellElements[i].classList.add('o')
            oCount++;
        } i++
    } while (i < boardCheck.length);

    const sleep = (ms) => new Promise(resolve => setTimeout(resolve, ms));
    async function delayLoop() {
        await sleep(1000);
        boardDetails();
    }
    delayLoop();
}

async function saveDetails(sym, loc){
	const date = new Date();
    const year = date.getFullYear();
    const month = date.getMonth();
    const day = date.getDate();
    const hour = date.getHours();
    const minutes = date.getMinutes();
    const seconds = date.getSeconds();
    
    
    const datesaved = `${year}-${month}-${day} ${hour}:${minutes}:${seconds}`;

    const playerIdTemp = gameKey + sym;
    
    fetch(`http://localhost:8080/TicTacToeRS/rest/save`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            gameId: gameKey,
            playerId: playerIdTemp,
            symbol: sym,
            location: loc,
            datesaved: datesaved
        })
    })
}

async function viewHistory(playerId) {
    const response = await fetch(`http://localhost:8080/TicTacToeRS/rest/listgames/${playerId}`, {
        headers: {
            'Content-Type': 'application/json',
        }
    }).catch(e => showErrorPage());

    const data = await response.json();
    
    const gamesList = data.list;
    // const listLength = gamesList.length;
    console.log(data);
    console.log(gamesList);
}

async function viewMovesRecord(e) {
    const response = await fetch(`http://localhost:8080/TicTacToeRS/rest/getgame/${gameKey}`, {
        headers: {
            'Content-Type': 'application/json',
        }
    }).catch(e => showErrorPage());

    const data = await response.json();
    const movesList = data.list;

    console.log(data);
    console.log(movesList);
}