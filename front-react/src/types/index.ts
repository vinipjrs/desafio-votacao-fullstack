export interface Pauta {
    id: number;
    titulo: string;
    descricao: string;
    criadoEm: string;
    sessaoAtiva: boolean;
    dataFechamentoSessao?: string;
}
export interface SessaoVotacao {
    id: number;
    dataAbertura: string;
    dataFechamento: string;
    pauta: Pauta;
}
export type VotoOpcao = 'SIM' | 'NAO';
export interface VotoRequest {
    cpf: string;
    opcao: VotoOpcao;
}
export interface ResultadoVotacao {
    pautaId: number;
    titulo: string;
    votosSim: number;
    votosNao: number;
    totalVotos: number;
}
