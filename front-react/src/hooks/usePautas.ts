import { useState, useEffect } from 'react';
import api from '../api/api';
import type { Pauta } from '../types';

export function usePautas() {
  const [pautas, setPautas] = useState<Pauta[]>([]);
  const [loading, setLoading] = useState(true);

  const fetchPautas = async () => {
    try {
      const res = await api.get<Pauta[]>('/pautas');
      setPautas(res.data);
    } catch (err) {
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchPautas();
  }, []);

  return { pautas, loading, fetchPautas };
}
