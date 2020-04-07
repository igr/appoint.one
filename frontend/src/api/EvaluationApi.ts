import http from '@/utils/http';
import { NewEvaluation } from '@/model/NewEvaluation';

class EvaluationApi {
  postNewEvaluation = (timeslotId: number, data: NewEvaluation) => http({
    url: 'evaluations',
    method: 'post',
    data: {
      data: {
        ...data,
        success: data.success === 1,
        forward: data.forward === 1,
      },
      timeslotId,
    },
  });
}

export default new EvaluationApi();
