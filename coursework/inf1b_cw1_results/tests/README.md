# Tests

Basic tests: 
    - basic functionality as described in the paper
Advanced tests:
    - corner cases
    - invalid input

FoxHoundGame
> No tests here

FoxHoundUtils
- initialisePositions
 x   B: default dim, return not null array with correct entries
 x   B: input negative dim, throw exception
 x   A: input lower and upper bound: return not null array with correct entries
 x   A: input odd dim, even half: return not null array with correct values
 x   A: input odd dim, odd half: return not null array with correct values
 x   A: input even dim, even half: return not null array with correct values
 x   A: input even dim, odd half: return not null array with correct values
 x   A: input too small dim, throw exception
 x   A: input too large dim, throw exception

- isFoxWin
 x   B: fox in winning position, default dimension, return true
 x   B: fox not in winning position, default dimension, return false
 x   B: null players array, throw exception
 x   A: fox win and lose based on non default dimensions
 x   A: invalid board coordinates for fox in player array (incorrect format), throw exception

- isHoundWin
 x   B: All four corners are taken by a hound, return true
 x   B: One of the corners is still free, return false
 x   B: players array is null, throw exception
 x   B: dimension is invalid, throw exception
 x   A: check for unusual board coordinates
 x   A: Fox is backed against left or right wall, return true.
 x   A: Fox is backed against bottom wall, return true.
 x   A: Fox is backed into the corner of the field, return true.
 x   A: valid players array, invalid dimension (multiple values), throw exception
 x   A: invalid board coordinates (not board coord or null) for hounds in player array, throw exception
 x   A: players array not matching with board coordinates, throw exception

- isValidMove
 x   B: valid move, default dim, return true
 x   B: figure does not match origin, return false
 x   B: origin does not contain a figure, return false
 x   B: field is already taken by other figure, return false
 x   A: valid move, non default setup, return true
 x   A: origin and dest are too far apart, return false
 x   A: hound moves backwards, return false
 x   A: origin and dest are the same, return false
 x   A: any of the values are invalid, throw exception
        

FoxHoundUI
- displayBoard
 x   - B: player array, dim 8, check console print
 x   - A: player array, dim 5, check console print
 x   - A: player array, dim 9, check console print
 x   - A: player array, dim 10, check console print (leading zero)
 x   - A: player array null, throw exception
 x   - A: player array contains invalid entries or null, throw exception
 x   - A: player array valid, dim invalid, throw exception
- positionQuery
 x   - B: valid dimension, check input text as in example
 x   - B: valid dimension, invalid input, error text as in example
 x   - B: valid dimension, valid input, correct result returned
 x   - B: players array has dimension other than 8
 x   - A: valid dimension, invalid input in some other ways, error text as in example
 x   - A: invalid dimension, throw exception
 x   - A: scanner null, throw exception
 x   - A: valid dimension High or low, check input text as in example
- fileQuery
 x   - B: question text
 x   - B: Path returned as entered
 x   - A: Scanner is null, throw exception

FoxHoundIO
- loadGame
 x   - B: valid players, valid path, update players and return value
 x   - B: valid players, valid path, invalid file content, do nothing and return error value
 x   - B: valid players, invalid path, change nothing and return error value
 x   - B: path null, throw exception
 x   - A: players is null throw exception
 x   - A: invalid players, throw exception
 x   - A: file is a folder, change nothing, return error value
- saveGame
 x   - B: valid players, valid move, valid path, save file as expected (use load function to test this)
 x   - B: valid players, valid move, invalid path, return false
 x   - B: players array has dimension other than 8
 x   - B: Path is null, throw exception
 x   - A: players is null, throw exception
 x   - A: invalid players, throw exception
 x   - A: invalid move, throw exception
 x   - A: path already exists, return false
