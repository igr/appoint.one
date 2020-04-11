import { Doctor } from '@/model/Doctor';

export interface DataPair {
  value: number;
  text: string;
}

export const occupations: DataPair[] = [
  {
    value: 1,
    text: 'Psihijatar',
  },
  {
    value: 2,
    text: 'Psiholog',
  },
  {
    value: 3,
    text: 'Socijalni radnik',
  },
  {
    value: 4,
    text: 'Lekar specijalista *',
  },
  {
    value: 5,
    text: 'Lekar na specijalzaciji *',
  },
  {
    value: 6,
    text: 'Teolog',
  },
  {
    value: 7,
    text: 'Andragog',
  },
  {
    value: 8,
    text: 'Specijalni pedagog',
  },
  {
    value: 9,
    text: 'Defektolog',
  },
  {
    value: 10,
    text: 'Pedagog',
  },
  {
    value: 11,
    text: 'Student medicine',
  },
  {
    value: 12,
    text: 'Student psihologije',
  },
  {
    value: 13,
    text: 'Student soc. rada',
  },
  {
    value: 14,
    text: 'Student teologije',
  },
  {
    value: 15,
    text: 'Student pedagogije',
  },
  {
    value: 16,
    text: 'Student andragogije',
  },
  {
    value: 17,
    text: 'Student defektologije',
  },
  {
    value: 999,
    text: 'Drugo',
  },
];

export function occupationOf(doc: Doctor, forPublic = true): string {
  if (forPublic) {
    if (doc.data.certificate === 0) {
      return 'Psihoterapeut pod supervizijom';
    }
    return 'Psihoterapeut';
  }
  const value = doc.data.occupation;
  const lookup = occupations.find((it) => it.value === value);
  if (lookup === undefined) {
    throw new TypeError(`Invalid occupation ID ${value}`);
  }
  return lookup.text;
}

export const modalitets = [
  {
    value: 1,
    text: 'KBT',
  },
  {
    value: 2,
    text: 'REBT',
  },
  {
    value: 3,
    text: 'Sistemska porodična terapija',
  },
  {
    value: 4,
    text: 'Geštalt',
  },
  {
    value: 5,
    text: 'Psihoanaliza',
  },
  {
    value: 6,
    text: 'Psihoanalitička psihoterapija',
  },
  {
    value: 7,
    text: 'Psihoanalitička grupna terapija',
  },
  {
    value: 8,
    text: 'Psihodrama',
  },
  {
    value: 9,
    text: 'TA',
  },
  {
    value: 10,
    text: 'Integrativna psihoterapija',
  },
  {
    value: 11,
    text: 'Integrativna art terapija',
  },
  {
    value: 12,
    text: 'Klijentom centrirana psihoterapija',
  },
  {
    value: 13,
    text: 'Konstruktivistička psihoterapija',
  },
  {
    value: 14,
    text: 'Telesna psihoterapija',
  },
  {
    value: 15,
    text: 'Hipnoterapija',
  },
  {
    value: 16,
    text: 'Logoterapija',
  },
  {
    value: 17,
    text: 'Shema terapija',
  },
  {
    value: 18,
    text: 'EMDR',
  },
  {
    value: 19,
    text: 'Analiza snova',
  },
  {
    value: 999,
    text: 'Drugo',
  },
];

export function modalitetOf(value: number) {
  const lookup = modalitets.find((it) => it.value === value);
  if (lookup === undefined) {
    throw new TypeError(`Invalid occupation ID ${value}`);
  }
  return lookup.text;
}
