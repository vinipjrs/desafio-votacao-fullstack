# 🧠 Master Learnings & Post-Mortems

Documento vivo para registrar evoluções técnicas, decisões de arquitetura e aprendizado contínuo do projeto.

## 📝 Decisões de Arquitetura (Kaizen)
- **Mobile-First**: Optamos por um layout flutuante (floating dock) para focar na experiência do associado no dia das reuniões.
- **Sessão Automática**: Removemos o passo manual de abrir sessão para diminuir a fricção no primeiro voto. O sistema abre 1 min por padrão.
- **Sentry Integration**: Essencial para não "voar às cegas". Erros de prod agora são alertas reais.
- **Global Exception Handling**: Evitamos o vazamento de stack traces do Spring, retornando JSON padronizado.

## 🛠️ Post-Mortem: Incidentes Resolvidos
### Data: 25/03/2026 - Crash no primeiro voto
- **Problema**: O frontend tentava ler `dataFechamentoSessao` de uma pauta sem sessão aberta.
- **Causa**: Falta de verificação de nulo no Reducer/State.
- **Solução**: Implementado fallback visual (Placeholder) e trigger automático de sessão.
- **Aprendizado**: DTOs devem ser sempre consistentes entre Back e Front (Typescript types atualizados).

## 🚀 Próximos Passos (Evolução Contínua)
- [ ] Implementar notificações Push via Web Push API.
- [ ] Otimizar performance do Redis para milhões de votos simultâneos.
- [ ] Adicionar suporte a múltiplas sessões por pauta se necessário.

## 🎓 Master Learnings: Evolução Profissional
- **Resiliência**: Implementação de `GlobalExceptionHandler` no Java protege o sistema contra crashes inesperados.
- **Observabilidade**: Sentry integrado no Front e Back para monitoramento proativo.
- **Padronização**: Uso de DTOs e Typescript Types para garantir contrato entre as camadas.

