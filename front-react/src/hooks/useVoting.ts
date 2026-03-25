import { useState } from 'react';
import api from '../api/api';
import type { VotoOpcao } from '../types';

export function useVoting(id: string | undefined) {
  const [acting, setActing] = useState(false);

  const handleOpenSession = async (minutos: number) => {
    try {
      setActing(true);
      await api.post(`/pautas/${id}/sessoes`, { minutosDuracao: minutos });
      // Sessão abre via API pra garantir que o tempo conte pra todo mundo igual
      return true;
    } catch (err: any) {
      // Por enquanto um alert resolve, depois a gente coloca um Toast bonitão
      alert(err.response?.data?.mensagem || 'Erro ao abrir sessão');
      return false;
    } finally {
      setActing(false);
    }
  };

  const handleVote = async (cpf: string, opcao: VotoOpcao) => {
    if (!cpf) {
      alert('Informar o CPF é obrigatório, amigão.');
      return false;
    }
    try {
      setActing(true);
      await api.post(`/pautas/${id}/votos`, { cpf, opcao });
      return true;
    } catch (err: any) {
      alert(err.response?.data?.mensagem || 'Erro ao registrar voto');
      return false;
    } finally {
      setActing(false);
    }
  };

  return { acting, handleOpenSession, handleVote };
}
