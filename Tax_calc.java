//This class calculates all tax liabilities and profit/loss made on transactions
class Tax_calc
{
    /* Different taxes are Exchange Transaction Charges, Securities 
       Transactions Tax, Brokerage by the Broker, Depository Charges,
       Stamp Duty, SEBI Turnover Fees and Goods and Services Tax */
    double [] share_price, brok, stt, etc, gst, sum, profit, SEBITurnoverFees;
    double stampDuty;
    int share_no;
    private Tax_window mainWindow;
    public Tax_calc(Tax_window window)
    {
        share_no = 0;
        profit = new double[3];
        share_price = new double[2];
        brok = new double[2];
        stt = new double[2];
        etc = new double[2];
        gst = new double[2];
        sum = new double[2];
        SEBITurnoverFees = new double[2];
        mainWindow = window;
    }
    public void Profit(int ShareNumber, double SharePrice[])
    {
        profit[0] = ShareNumber*(SharePrice[1] - SharePrice[0]);
        profit[1] = profit[0] - sum[0] - sum[1];
        mainWindow.display(sum, profit);
    }
    public void Sums(boolean ID)
    {
        for(int i=0;i<2;i++) { 
            sum[i] = brok[i] + stt[i] + etc[i] + gst[i] + SEBITurnoverFees[i]; 
        }
        if(ID != true) {sum[1] += 19.75; }
        sum[0] += stampDuty;
    }
    public void SEBI(int ShareNumber, double SharePrice[])
    {
        for(int i=0;i<2;i++) { SEBITurnoverFees[i] = 0.000001*ShareNumber*SharePrice[i];}
    }
    public void StampDuty(int ShareNumber, double SharePrice[])
    {
        stampDuty = 0.00015*ShareNumber*SharePrice[0];
    }
    public void GST(int ShareNumber, double SharePrice[], boolean ID)
    {
        gst[0] = 0.18*(brok[0] + etc[0]);
        if(ID == true) { gst[1] = 0.18*(brok[1] + etc[1]); }
        else { gst[1] = 0.18*(brok[1] + etc[1] + 19.75); }
    }
    public void ETC(int ShareNumber, double SharePrice[])
    {
        for(int i=0;i<2;i++) { etc[i] = 0.000037*ShareNumber*SharePrice[i];}
    }
    public void STT(int ShareNumber, double SharePrice[], boolean ID)
    {
        if(ID == true) { for(int i=0;i<2;i++) { stt[i] = 0.00025*ShareNumber*SharePrice[i];}}
        else { for(int i=0;i<2;i++) { stt[i] = 0.001*ShareNumber*SharePrice[i];}}
    }
    public void Brokerage(int ShareNumber, double SharePrice[])
    {
        for(int i=0;i<2;i++)
        {
            double v = 0.001*ShareNumber*SharePrice[i];
            if(v<=20.0){ brok[i] = v; }
            else { brok[i] = 20.0; }
        }
    }
    public void caller(int ShareNumber, double SharePrice[], boolean ID)
    {
        this.Brokerage(ShareNumber, SharePrice);
        this.STT(ShareNumber, SharePrice, ID);
        this.ETC(ShareNumber, SharePrice);
        this.GST(ShareNumber, SharePrice, ID);
        this.Sums(ID);
        this.Profit(ShareNumber, SharePrice);
        this.StampDuty(ShareNumber,SharePrice);
        this.SEBI(ShareNumber, SharePrice);
    }
}