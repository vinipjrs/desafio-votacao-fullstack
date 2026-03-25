import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ArrowLeft, Sparkles, Send } from 'lucide-react';
import api from '../api/api';

export default function NovaPauta() {
  const [titulo, setTitulo] = useState('');
  const [descricao, setDescricao] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!titulo || !descricao) return alert('Campos obrigatórios!');
    try {
      setLoading(true);
      await api.post('/pautas', { titulo, descricao });
      navigate('/');
    } catch { alert('Erro ao criar pauta'); } finally { setLoading(false); }
  };
  return (
    <div className="space-y-8 animate-in fade-in slide-in-from-bottom-4 duration-700">
      <header className="px-2 pt-10 flex flex-col gap-4">
        <button onClick={() => navigate(-1)} className="flex items-center gap-2 text-slate-400 font-bold text-[10px] uppercase hover:text-[#008a51]"><ArrowLeft size={14} /> Voltar</button>
        <div>
          <h1 className="text-4xl font-bold text-slate-800 mb-2 flex items-center gap-3"><Sparkles className="text-[#008a51]" size={32} /> Nova Pauta</h1>
          <p className="text-slate-500">Tema para a votação da assembleia.</p>
        </div>
      </header>
      <form onSubmit={handleSubmit} className="glass-card p-8 space-y-8">
        <div className="space-y-2">
          <label className="text-[10px] font-bold text-slate-400 uppercase tracking-widest ml-2">Título</label>
          <input className="input-verdant" value={titulo} onChange={e => setTitulo(e.target.value)} placeholder="Ex: Reforma" />
        </div>
        <div className="space-y-2">
          <label className="text-[10px] font-bold text-slate-400 uppercase tracking-widest ml-2">Descrição</label>
          <textarea rows={5} className="input-verdant resize-none" value={descricao} onChange={e => setDescricao(e.target.value)} placeholder="Explique os objetivos..." />
        </div>
        <button type="submit" disabled={loading} className="btn-primary w-full py-5 text-xl">{loading ? 'Criando...' : <><Send size={20} /> Propor Pauta</>}</button>
      </form>
    </div>
  );
}
