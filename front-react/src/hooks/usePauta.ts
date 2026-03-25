import { useState, useEffect } from 'react';
import api from '../api/api';
import type { Pauta, ResultadoVotacao } from '../types';

export function usePauta(id: string | undefined) {
  const [pauta, setPauta] = useState<Pauta | null>(null);
  const [resultado, setResultado] = useState<ResultadoVotacao | null>(null);
  const [loading, setLoading] = useState(true);

  const fetchPauta = async () => {
    try {
      const res = await api.get<Pauta>(`/pautas/${id}`);
      setPauta(res.data);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const fetchResultado = async () => {
    try {
      const res = await api.get<ResultadoVotacao>(`/pautas/${id}/resultado`);
      setResultado(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    if (id) {
      fetchPauta();
      fetchResultado();
    }
  }, [id]);

  return { pauta, resultado, loading, fetchPauta, fetchResultado };
}
