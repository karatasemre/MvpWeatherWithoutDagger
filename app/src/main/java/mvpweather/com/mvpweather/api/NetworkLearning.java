package mvpweather.com.mvpweather.api;

import com.oneframe.android.networking.listener.BaseNetworkResponseListener;
import com.oneframe.android.networking.model.ErrorModel;
import com.oneframe.android.networking.model.ResultModel;

/**
 * Created by Emre.Karatas on 21.03.2019.
 */

public class NetworkLearning extends com.oneframe.android.networking.NetworkLearning<String> {

    @Override
    public void sendError(BaseNetworkResponseListener listener, ErrorModel<String> errorModel) {
        super.sendError(listener, errorModel);
    }

    @Override
    public void checkCustomError(BaseNetworkResponseListener baseNetworkResponseListener, ErrorModel<String> errorModel) {

    }

    @Override
    public void checkSuccess(BaseNetworkResponseListener baseNetworkResponseListener, ResultModel resultModel) {

        baseNetworkResponseListener.onSuccess(resultModel);

    }

    @Override
    public <ResultType> ResultType getModelFromJSON(String s, Class<ResultType> aClass) {
        return null;
    }
}
