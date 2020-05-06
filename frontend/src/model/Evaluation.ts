import { EvaluationData } from '@/model/EvaluationData';

export interface Evaluation {
  id: number;
  data: EvaluationData;
  timeslotId: number;
}
