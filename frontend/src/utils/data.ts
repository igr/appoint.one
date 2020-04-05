export interface DataPair {
  value: number,
  text: String,
}

export const occupations: DataPair[] = [
  {
    value: 1,
    text: 'psihijatar',
  },
  {
    value: 2,
    text: 'psiholog',
  },
  {
    value: 3,
    text: 'socijalni radnik',
  },
  {
    value: 4,
    text: 'lekar specijalista *',
  },
  {
    value: 5,
    text: 'lekar na specijalzaciji *',
  },
  {
    value: 6,
    text: 'teolog',
  },
  {
    value: 7,
    text: 'andragog',
  },
  {
    value: 8,
    text: 'specijalni pedagog',
  },
  {
    value: 9,
    text: 'defektolog',
  },
  {
    value: 10,
    text: 'pedagog',
  },
  {
    value: 11,
    text: 'student medicine',
  },
  {
    value: 12,
    text: 'student psihologije',
  },
  {
    value: 13,
    text: 'student soc.rada',
  },
  {
    value: 14,
    text: 'student teologije',
  },
  {
    value: 15,
    text: 'student pedagogije',
  },
  {
    value: 16,
    text: 'student andragogije',
  },
  {
    value: 17,
    text: 'student defektologije',
  },
  {
    value: 999,
    text: 'Drugo',
  },
];

export function occupationOf(value: Number): String {
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

export function modalitetOf(value: Number) {
  const lookup = modalitets.find((it) => it.value === value);
  if (lookup === undefined) {
    throw new TypeError(`Invalid occupation ID ${value}`);
  }
  return lookup.text;
}
