


public class Game
{
    //Передаётся предполагаемое состояние игры, координаты "псевдохода" и цвет игрока совершающего этот ход.
    //Возвращается оценка этого "псевдохода".
    private int Mark(Game TemporaryField, int tmpX, int tmpY, int color) {
        //Оценка.
        int value = 0;
        int firstBuffer = 0;
        int secondBuffer = 0;

        //Если отсюда мы можем завершить линию из 4 фишек, то возвращаем максимальное значение.
        if (openLineLength(TemporaryField.grid[tmpX][tmpY], color) == 4) {
            return Integer.MAX_VALUE;
        }
        //проверяем количество направлений, в которые мы можем построить непрерывные линии из 5 фишек.
        for (int j = 0; j <= 4; j++) {
            if (amountOfLines(TemporaryField.grid[tmpX][tmpY], color) == j)
                value++;
        }

        //Если длина максимальной линии ротивника на поле больше нашей максимальной длины на поле,
        //то проверяем, можем ли мы блокировать эту линию противника этим ходом.
        //Иначе продолжаем свою линию.
        int enemyMaxLine = lineMaxLength(TemporaryField.grid, -color);
        int myMaxLine = lineMaxLength(TemporaryField.grid, color);
        if (enemyMaxLine > myMaxLine) {
            value += 2 + amountOfChips(TemporaryField.grid[tmpX][tmpY], -color);
        }
        else if(enemyMaxLine < myMaxLine){
            value += 2 + amountOfChips(TemporaryField.grid[tmpX][tmpY], color);
        }
        //Если ме максимальные длина нашей линии и линии противника совпадают, выясняем,
        //кто имеет преимущество по количеству таких линий.
        else if (enemyMaxLine == myMaxLine){
            if (lineCounter(enemyMaxLine, -color) > lineCounter(myMaxLine, color)){
                value -= lineCounter(enemyMaxLine, -color)*enemyMaxLine;
            }
            else{
                value += lineCounter(myMaxLine, color)*myMaxLine;
            }

        }

        //Проверка на "вилки". По правилам Рэндзю черным нельзя делать вилки.
        for (int x = tmpX - 1; x <= tmpX + 1; x++) {
            for (int y = tmpY - 1; y <= tmpY + 1; y++) {
                if (TemporaryField.grid[x][y] == color)
                        firstBuffer += 2;
                if (TemporaryField.grid[x][y] == -color)
                        secondBuffer += 1;
            }
        }
        if (color == 1 && firstBuffer >= 4 && secondBuffer >= 1) {
            return Integer.MIN_VALUE;
        }
        else{
            value += firstBuffer + secondBuffer;
        }
        return value;
        }
}