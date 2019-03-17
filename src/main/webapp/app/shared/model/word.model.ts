export interface IWord {
    id?: number;
    value?: string;
    frequency?: number;
}

export class Word implements IWord {
    constructor(public id?: number, public value?: string, public frequency?: number) {}
}
