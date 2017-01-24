

/**
 * Created by Admin on 16.12.2016.
 */
//Оценочная функция
public class Game
{
    //передаётся состояние игры. возвращается оценка этого состояние
    private int Mark(Game CurrentPos)
    {
        int mark;//оценка
        //если фишек на выигрышных полях больше, чем у противника, то оценка растет
        mark += CurrentPos.Me.ammountOfCheckersOnWinField - CurrentPos.Opponent.ammountOfChekersOnWinField;
        //чем больше преобладание на центральном поле, тем выше оценка
        mark += CurrentPos.Me.ammountOfCheckersOnCentralField - CurrentPos.Opponent.ammountOfChekersOnCentralField;
        //чем больше фишек на выигрышном поле, тем выше оценка
        mark += CurrentPos.Me.ammountOfCheckersOnStartField + CurrentPos.Me.ammountOfCheckersOnCentralField - CurrentPos.Me.ammountOfCheckersOnWinField;
        //чем больше фишек противника на стартовом поле, тем лучше
        mark -= CurrentPos.Me.ammountOfCheckersOnStartField - CurrentPos.Opponent.ammountOfChekersOnStartField;
        //оценка позиции каждой дружественной фишки
        for (int i = 0; i < CurrentPos.Me.ammountOfCheckers; i++)
        {   //если фишка блокирует фишку противника, то прибавляет
            if (blokingEnemy(CurrentPos.Me.Chekers[i]))
                mark++;
            //если дает противнику перешагнуть через себя, то отнимает
            if (unblokingEnemy(CurrentPos.Me.Chekers[i]))
                mark--;
            //если на центральном поле - прибаляет
            if (onCentralField(CurrentPos.Me.Chekers[i]))
                //прибавление зависит от того насколько далеко выигрышное поле
                mark += howFarIsWinField(CurrentPos.Me.Chekers[i]);
            //если на выигрышном поле прибаляет
            if (onWinField(CurrentPos.Me.Chekers[i]))
        //прибавление зависит от того насколько далеко на выигрышном поле
        mark += howFarOnWinField(CurrentPos.Me.Chekers[i]);
        //оценка отнимается, если фишка на стартовом поле
        if (onStartField(CurrentPos.Me.Chekers[i]))
            //отнимается в зависимости от того, насколько "глубоко" на стартовом поле
            mark -= howFarOnStartField(CurrentPos.Me.Chekers[i]);
    }
        //оценка позиции каждой вражеской фишки
        for (int i = 0; i < CurrentPos.Opponent.ammountOfCheckers; i++)
        {   //если фишка блокирует дружественную фишку, то отнимает
            if (blokingEnemy(CurrentPos.Opponent.Chekers[i]))
                mark--;
            //если дает дружественной фишке перешагнуть через себя, то прибавляет
            if (unblokingEnemy(CurrentPos.Opponent.Chekers[i]))
                mark++;
            //если на центральном поле - отнимает
            if (onCentralField(CurrentPos.Opponent.Chekers[i]))
                //отнимает в зависимости от того насколько далеко выигрышное поле
                mark -= howFarIsWinField(CurrentPos.Opponent.Chekers[i]);
            //если на выигрышном поле - отнимает
            if (onWinField(CurrentPos.Opponent.Chekers[i]))
                //отнимание зависит от того насколько далеко на выигрышном поле
                mark += howFarOnWinField(CurrentPos.Opponent.Chekers[i]);
            //оценка прибавляется, если фишка на стартовом поле
            if (onStartField(CurrentPos.Opponent.Chekers[i]))
                //прибавляется в зависимости от того, насколько далеко на стартовом поле
                mark += howFarOnStartField(CurrentPos.Opponent.Chekers[i]);
        }
        return mark;
    }
}
